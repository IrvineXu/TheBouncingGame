package irvine.angle;

/**
 * Simple pseudo-physic engine
 */
public class AnglePhysicsEngine extends AngleObject
{
	public AngleVector mGravity;
	public float mViscosity;

	public AnglePhysicsEngine(int maxObjects)
	{
		super(maxObjects);
		mGravity = new AngleVector();
		mViscosity = 0;
	}

	protected void physics(float secondsElapsed)
	{
		for (int o = 0; o < mChildsCount; o++)
		{
			if (mChilds[o] instanceof AnglePhysicObject)
			{
				AnglePhysicObject mChildO = (AnglePhysicObject) mChilds[o];
				// Gravity
				mChildO.mVelocity.mX += mChildO.mMass * mGravity.mX * secondsElapsed;
				mChildO.mVelocity.mY += mChildO.mMass * mGravity.mY * secondsElapsed;
				if ((mChildO.mVelocity.mX != 0) || (mChildO.mVelocity.mY != 0))
				{
					// Air viscosity
					if (mViscosity > 0)
					{
						float surface = mChildO.getSurface();
						if (surface > 0)
						{
							float decay = surface * mViscosity * secondsElapsed;
							if (mChildO.mVelocity.mX > decay)
								mChildO.mVelocity.mX -= decay;
							else if (mChildO.mVelocity.mX < -decay)
								mChildO.mVelocity.mX += decay;
							else
								mChildO.mVelocity.mX = 0;
							if (mChildO.mVelocity.mY > decay)
								mChildO.mVelocity.mY -= decay;
							else if (mChildO.mVelocity.mY < -decay)
								mChildO.mVelocity.mY += decay;
							else
								mChildO.mVelocity.mY = 0;
						}
					}
				}
				// Velocity
				mChildO.mDelta.mX = mChildO.mVelocity.mX * secondsElapsed;
				mChildO.mDelta.mY = mChildO.mVelocity.mY * secondsElapsed;
			}
		}
	}


	protected void kynetics(float secondsElapsed)
	{
		for (int o = 0; o < mChildsCount; o++)
		{
			if (mChilds[o] instanceof AnglePhysicObject)
			{
				AnglePhysicObject mChildO = (AnglePhysicObject) mChilds[o];
				if ((mChildO.mDelta.mX != 0) || (mChildO.mDelta.mY != 0))
				{
					// Collision
					mChildO.mPosition.mX += mChildO.mDelta.mX;
					mChildO.mPosition.mY += mChildO.mDelta.mY;
					for (int c = 0; c < mChildsCount; c++)
					{
						if (c != o)
						{
							if (mChilds[c] instanceof AnglePhysicObject)
							{
								AnglePhysicObject mChildC = (AnglePhysicObject) mChilds[c];
								if (mChildO.collide(mChildC))
								{
									mChildO.mPosition.mX -= mChildO.mDelta.mX;
									mChildO.mPosition.mY -= mChildO.mDelta.mY;
									mChildC.mDelta.mX = mChildC.mVelocity.mX * secondsElapsed;
									mChildC.mDelta.mY = mChildC.mVelocity.mY * secondsElapsed;
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void step(float secondsElapsed)
	{
		physics(secondsElapsed);
		kynetics(secondsElapsed);
		super.step(secondsElapsed);
	}
}
