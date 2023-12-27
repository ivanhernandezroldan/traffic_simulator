package simulator.model;

public enum Weather {
	SUNNY("sun.png"), CLOUDY("cloud.png"), 
	RAINY("rain.png"), WINDY("wind.png"), 
	STORM("storm.png");
	
	private String imageSrc;
	
	private Weather (String src){
		this.imageSrc = src;
	}

	public String getImageSrc() {
		return imageSrc;
	}
}
