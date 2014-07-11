package com.viva.vivalistening.commands;

import android.app.Activity;
import android.os.Bundle;

import com.viva.vivalistening.ICommandActivity;

abstract public class Command {
	//public abstract void execute(Args arg);
	public abstract void execute(Bundle arg,ICommandActivity activity);
}
