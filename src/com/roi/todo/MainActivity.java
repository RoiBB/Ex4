package com.roi.todo;

import java.util.ArrayList;

import com.roi.todo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	private static TaskListAdapter taskListAdapter;
	private static ArrayList<Task> taskArrayList;
	private static TaskListModel taskListModel;
	
	private static final int ADD_TASK_SIMPLE = 100;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
        ListView listView = (ListView) findViewById(R.id.main_item_list);
        
        taskListModel = TaskListModel.getInstance(this);
        taskArrayList = taskListModel.getAllTasks();
        
        taskListAdapter = new TaskListAdapter(this,taskArrayList);
        listView.setAdapter(taskListAdapter);
    }
    
    public void goToCreateTaskActivity(View view)
    {
    	Intent intent = new Intent(this, CreateTaskActivity.class);
    	startActivityForResult(intent, ADD_TASK_SIMPLE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
    	if (requestCode == ADD_TASK_SIMPLE)
    	{
	    	if (resultCode == RESULT_OK)
	    	{
	    		Task newTask = new Task(data.getStringExtra(CreateTaskActivity.TASK_DESCRIPTION));
	    		
	    		Log.d("ID: ",Integer.toString(newTask.getId()));		// id check
	    		
	    		taskListModel.addTask(newTask);
	    		taskArrayList.add(0, newTask);
	    		taskListAdapter.notifyDataSetChanged();
	    	}
	    	
	    	if (resultCode == RESULT_CANCELED)
	    	{
	    		Toast.makeText(this, "Please enter description.", Toast.LENGTH_SHORT).show();
	    	}
    	}
	}

}