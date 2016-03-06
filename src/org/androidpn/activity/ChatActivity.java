package org.androidpn.activity;

import org.androidpn.demoapp.R;
import org.androidpn.packet.Chat;
import org.androidpn.util.Constants;
import org.androidpn.xmpp.XmppManager;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ChatActivity extends Activity {
	private Button sendBtn;
	private TextView nickname, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		initView();
	}

	private void initView() {
		sendBtn = (Button) findViewById(R.id.send);
		nickname = (TextView) findViewById(R.id.nickname);
		content = (TextView) findViewById(R.id.content);

		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = nickname.getText().toString();
				String c = content.getText().toString();
				XmppManager xmppManager = Constants.xmppManager;

				Chat chat = new Chat();

				PacketFilter packetFilter = new AndFilter(new PacketIDFilter(chat.getPacketID()), new PacketTypeFilter(
						IQ.class));

				XMPPConnection connection = xmppManager.getConnection();
				connection.addPacketListener(packetListener, packetFilter);

				chat.setType(IQ.Type.RESULT);
				chat.addAttribute("content", c);
				chat.addAttribute("nickname", name);

				connection.sendPacket(chat);
			}
		});
	}

	private PacketListener packetListener = new PacketListener() {

		public void processPacket(Packet packet) {
			System.out.println(">>>>>>>>>>>>>>>>" + packet);
		}
	};
}
