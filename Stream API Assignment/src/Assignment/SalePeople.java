package Assignment;

public class SalePeople 
{
	private String name;
	private String city;
	private float comm;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getComm() {
		return comm;
	}
	public void setComm(float comm) {
		this.comm = comm;
	}
	public SalePeople(String name, String city, float comm) {
		super();
		this.name = name;
		this.city = city;
		this.comm = comm;
	}

	public boolean equalCommision(float com)
	{
		return this.comm == com;
	}
	@Override
	public String toString() {
		return "SalePeople [name=" + name + ", city=" + city + ", comm=" + comm + "]";
	}
}
