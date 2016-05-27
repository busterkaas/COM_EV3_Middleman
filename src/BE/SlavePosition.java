package BE;

public class SlavePosition {

	
	private float posX, posY, heading, clientDistanceX, clientDistanceY;
	
	public SlavePosition(float x, float y, float heading, float clientDistanceX, float clientDistanceY){
		posX = x;
		posY = y;
		this.heading = heading;
		this.clientDistanceX = clientDistanceX;
		this.clientDistanceY = clientDistanceY;
	}
	public float getHeading(){
		return heading;
	}
	
	public void setHeading(float value){
		heading = value;
	}
	
	public float getPosX(){
		return posX;
	}
	public float getPosY(){
		return posY;
	}
	
	public void setPosX(float xValue){
		posX = xValue;
	}
	
	public void setPosY(float yValue){
		posY = yValue;
	}
	
	public String stringPlacementPosition(){
		return (posX+clientDistanceX)+"|"+(posY+clientDistanceY) + "|"+heading;
	}
}
