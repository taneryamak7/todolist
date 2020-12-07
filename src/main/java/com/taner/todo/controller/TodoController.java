package com.taner.todo.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taner.todo.model.Todo;
import com.taner.todo.model.User;
import com.taner.todo.service.ITodoService;
import com.taner.todo.service.UserService;

@Controller
public class TodoController {

	@Autowired
	private ITodoService todoService;

	@Autowired
	private UserService service;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model) {
		String name = getLoggedInUserName(model);
		List<Todo> todos = todoService.getTodosByUser(name);
		Collections.sort(todos, new Comparator<Todo>() {

			public int compare(Todo o1, Todo o2) {
				return o1.getDuedate() != null && o2.getDuedate() != null ? o1.getDuedate().compareTo(o2.getDuedate())
						: 0;
			}
		});
		model.put("todos", todos);

		return "todo_list";
	}

	@RequestMapping(value = "/view-todos/{id}", method = RequestMethod.GET)
	public String viewTodos(@PathVariable(name = "id") int id, ModelMap model) {
		User user = service.get(id);
		List<Todo> todos = todoService.getTodosByUser(user.getUsername());
		Collections.sort(todos, new Comparator<Todo>() {

			public int compare(Todo o1, Todo o2) {
				return o1.getDuedate() != null && o2.getDuedate() != null ? o1.getDuedate().compareTo(o2.getDuedate())
						: 0;
			}
		});
		model.put("todos", todos);

		return "todo_list";
	}

	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo());
		return "create_work";
	}

	@RequestMapping(value = "/delete-todo/{id}", method = RequestMethod.GET)
	public String deleteTodo(@PathVariable(name = "id") int id) {
		todoService.deleteTodo(id);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/update-todo/{id}", method = RequestMethod.GET)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		todoService.updateTodo(todo);
		return "update_work";
	}

	@RequestMapping(value = "/update-todos/{id}", method = RequestMethod.POST)
	public String updateTodos(ModelMap model, @Valid Todo todo, BindingResult result) {

		todo.setWork(todo.getWork());
		todo.setStatus(todo.getStatus());
		todo.setDuedate(todo.getDuedate());
		todoService.updateTodo(todo);
		return "todo_list";
	}

	@RequestMapping(value = "/save-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @ModelAttribute("todo") Todo todo, BindingResult result) {

		todo.setUsername(getLoggedInUserName(model));
		todoService.saveTodo(todo);
		return "redirect:/list-todos";
	}
}