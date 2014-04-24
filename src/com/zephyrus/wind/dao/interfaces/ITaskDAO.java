package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

public interface ITaskDAO extends IDAO<Task>{
		ArrayList<Task> findActualTasksByUser(User user) throws Exception;
		ArrayList<Task> findAvailableTasksByRole(UserRole role) throws Exception;
}
