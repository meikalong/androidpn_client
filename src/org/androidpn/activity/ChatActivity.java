package org.androidpn.activity;

import org.androidpn.client.NotificationReceiver;
import org.androidpn.client.NotificationReceiver.NotificationReceiverResult;
import org.androidpn.demoapp.R;
import org.androidpn.packet.Chat;
import org.androidpn.util.Constants;
import org.androidpn.xmpp.XmppManager;
import org.jivesoftware.smack.packet.IQ;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ChatActivity extends Activity implements NotificationReceiverResult {
	private Button sendBtn;
	private TextView nickname, content, name, message;
	private XmppManager xmppManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		NotificationReceiver receiver = (NotificationReceiver) Constants.notificationService.getNotificationReceiver();
		receiver.setResult(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		NotificationReceiver receiver = (NotificationReceiver) Constants.notificationService.getNotificationReceiver();
		receiver.setResult(null);
	}

	private void initView() {
		sendBtn = (Button) findViewById(R.id.send);
		nickname = (TextView) findViewById(R.id.nickname);
		content = (TextView) findViewById(R.id.content);
		name = (TextView) findViewById(R.id.name);
		message = (TextView) findViewById(R.id.message);

		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = nickname.getText().toString();
				String c = content.getText().toString();
				xmppManager = Constants.xmppManager;

				Chat chat = new Chat();

				chat.setType(IQ.Type.SEND);
				chat.addAttribute("content", c);
				chat.addAttribute("nickname", name);

				xmppManager.sendPacket(chat);
			}
		});
	}

	@Override
	public boolean result(Intent intent) {
		String notificationTitle = intent.getStringExtra(Constants.NOTIFICATION_TITLE);
		String notificationMessage = intent.getStringExtra(Constants.NOTIFICATION_MESSAGE);
		name.setText(notificationTitle);
		message.setText(notificationMessage);
		return false;
	}
}
