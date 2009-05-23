package ms.domain;

public class Task {

	private final String status;
	private final int id;

	public Task(int id, String status) {
		this.id = id;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public int getID() {
		return id;
	}
}
