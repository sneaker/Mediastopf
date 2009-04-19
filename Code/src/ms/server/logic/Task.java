package ms.server.logic;


public class Task {
	
	private int taskID;
	private String status;

	public Task(int taskID, String status) {
		this.setID(taskID);
		this.setStatus(status);
	}

	public void setID(int taskID) {
		this.taskID = taskID;
	}

	public int getID() {
		return taskID;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Task) {
        	Task task = (Task) obj;
            return ((status == null ? task.getStatus() == null : status.equalsIgnoreCase(task.getStatus()) &&
                    (taskID == 0 ? task.getID() == 0 : taskID == task.getID())));
        }
        return false;
    }
}
