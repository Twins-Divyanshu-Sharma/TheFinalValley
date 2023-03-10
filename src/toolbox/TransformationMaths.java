package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class TransformationMaths {
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
    public static Matrix4f createTransformationMatrix( Vector3f translation, float rx, float ry, float rz, float scale){
    	Matrix4f matrix = new Matrix4f();
    	matrix.setIdentity();
    	matrix.translate(translation, matrix, matrix);
    	matrix.rotate((float)Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
    	matrix.rotate((float)Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
    	matrix.rotate((float)Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
    	matrix.scale(new Vector3f(scale,scale,scale), matrix, matrix);
    	return matrix;
    }
    
    public static Matrix4f createViewMatrix(Camera camera){
    	Matrix4f viewmatrix = new Matrix4f();
    	viewmatrix.setIdentity();
    	Vector3f cameraPos = camera.getPosition();
    	Vector3f ncameraPos = new Vector3f(-(cameraPos.x), -(cameraPos.y), -(cameraPos.z));
    	viewmatrix.rotate((float)Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewmatrix, viewmatrix);
    	viewmatrix.rotate((float)Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewmatrix, viewmatrix);
    	viewmatrix.translate(ncameraPos, viewmatrix, viewmatrix);
    	return viewmatrix;
    }
}
