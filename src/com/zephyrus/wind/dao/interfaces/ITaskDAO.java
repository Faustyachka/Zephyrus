package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

/**
* The interface enforces the operations needed to deal with OracleTaskDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface ITaskDAO extends IDAO<Task>{	

	/**
	 * Method allows to get tasks for the given user and task status.
	 * @param User user - user for which it is necessary to find out tasks in defined status 
	 * @param int taskStatusId - Task status ID defined status of task												
	 * @return list of tasks for the given user and task status
	 */
	ArrayList<Task> findTasksByUser(User user, int taskStatusID) throws Exception;
	
	/**
	 * Method allows to get free tasks inside the definite UserRole.
	 * @param role user role for which it is necessary to find out free tasks
	 * @return list of available tasks 
	 */
	ArrayList<Task> findAvailableTasksByRole(UserRole role) throws Exception;
}
