package ms.client.logic;


public class Task {
	
	private int tasknum;
	private String status;

	public Task(int tasknum, String status) {
		this.setTasknum(tasknum);
		this.setStatus(status);
	}

	public void setTasknum(int tasknum) {
		this.tasknum = tasknum;
	}

	public int getTasknum() {
		return tasknum;
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
                    (tasknum == 0 ? task.getTasknum() == 0 : tasknum == task.getTasknum())));
        }
        return false;
    }
}
