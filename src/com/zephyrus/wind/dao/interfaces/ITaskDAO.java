package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.TaskStatus;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;
public interface ITaskDAO extends IDAO<Task>{	

	/**
	 * Method allows to get tasks for the given user and task status.
	 * @param user - user for which it is necessary to find out tasks in defined status 
	 * @param Task status ID defined status of task
	 * @return list of tasks 
	 */
	ArrayList<Task> findTasksByUser(User user, int taskStatusID) throws Exception;
	ArrayList<Task> findAvailableTasksByRole(UserRole role) throws Exception;
}
