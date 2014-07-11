package com.viva.vivalistening.listlistening;

import android.content.Intent;
import android.os.Bundle;

import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.commands.Command;
import com.viva.vivalistening.listening.ListeningActivity;

public class NotifyClickListeningItem extends Command{

	@Override
	public void execute(Bundle arg, ICommandActivity activity) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(activity.getActivity(),ListeningActivity.class);
		intent.putExtras(arg);
		activity.getActivity().startActivity(intent);
		
	}

}
