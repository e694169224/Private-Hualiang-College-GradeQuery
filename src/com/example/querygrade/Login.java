package com.example.querygrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/***
 * 登陆界面
 * 
 * @author yin
 *
 */
public class Login extends Activity implements OnClickListener{
	private Button loginBtn;
	private EditText accountText;
	private EditText passwordText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);
		initialize();
		
	}
	
	/***
	 * 初始化控件
	 */
	private void initialize(){
		loginBtn = (Button)findViewById(R.id.login_btn);
		accountText = (EditText)findViewById(R.id.account);
		passwordText = (EditText)findViewById(R.id.password);
		loginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.login_btn:
			String accountStr = accountText.getText().toString();
			String passwordStr = passwordText.getText().toString();
			Log.d("log", accountStr);
			if((accountStr.length() != 0) ){
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("account", accountStr);
			intent.putExtra("password", passwordStr);
			startActivity(intent);
			}else{
				Toast.makeText(this, "密码学号不允许为空", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
