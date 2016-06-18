package com.truongpham.todoapp;

/**
 * Created by ts-truong.pham on 6/13/16.
 */
public class TodoItem {

    int iD;
    String _task;
    String _dueDate;
    String _notes;
    String _priority;
    String _status;

    public TodoItem(String _task, String _dueDate, String _notes, String _priority, String _status) {
        this._task = _task;
        this._dueDate = _dueDate;
        this._notes = _notes;
        this._priority = _priority;
        this._status = _status;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String get_task() {
        return _task;
    }

    public void set_task(String _task) {
        this._task = _task;
    }

    public String get_dueDate() {
        return _dueDate;
    }

    public void set_dueDate(String _dueDate) {
        this._dueDate = _dueDate;
    }

    public String get_notes() {
        return _notes;
    }

    public void set_notes(String _notes) {
        this._notes = _notes;
    }

    public String get_priority() {
        return _priority;
    }

    public void set_priority(String _priority) {
        this._priority = _priority;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }
}
