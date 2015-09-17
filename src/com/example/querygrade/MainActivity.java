package com.example.querygrade;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
/***
 * ��ʾ�ɼ���ҳ��
 * @author yin
 *
 */
public class MainActivity extends Activity{
	private Map<String, String> map = new HashMap<String, String>();
	private List<Grade> list = new ArrayList<Grade>();
	private TableAdapter adapter;
	private ListView listView;
	private String accountStr;
	private String passwordStr;
	
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				adapter = new TableAdapter(list, MainActivity.this);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.list_view);
		Intent intent = getIntent();
		passwordStr = intent.getStringExtra("password");
		accountStr = intent.getStringExtra("account");
		getOneCook();
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, 1, 1, "����");
		menu.add(0, 2, 2, "�˳�");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == 1){
			Intent intent = new Intent(this, About.class);
			startActivity(intent);
		}else if(item.getItemId() == 2){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	

	/**
	 * ����jwc.hlu.edu.cn/����ȡ��һ��cook�����շ��������ص�һ����Ϊ��ASPSESSIONIDQQTSASRQ ��Cookie
	 */
	private  void getOneCook() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet("http://jwc.hlu.edu.cn/");
				try {
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						Header[] header = httpResponse.getHeaders("Set-Cookie");// ��÷��������ص�Cookie��Header�ֶ�Ϊ��Set-Cookie
						// ��Cookies��ӽ�map������������ʽȡ��Set-Cookie��Values
						map.put("ASPSESSIONIDQQTSASRQ", header[0].getValue()
								.trim().split(";")[0].split("=")[1]);
						
						getTwoCookieAndSendOneCookie();
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

	/**
	 * 1��get:jwc.hlu.edu.cn/zfxk/ ���͵�һ��cookie����õڶ�����Ϊ�� ASP.NET_SessionId ��Cookie
	 * 2����html�н�����post��Ҫ�õ������ݣ�_VIEWSTATE��_VIEWSTATEGENERATOR��RadioButtonList1��Button1
	 */
	private synchronized void getTwoCookieAndSendOneCookie() {
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet("http://jwc.hlu.edu.cn/zfxk");
				httpGet.addHeader("Cookie", map.get("ASPSESSIONIDQQTSASRQ")
						.toString());
				try {
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						//��ȡָ��httpͷ��ָ��Ԫ��
						Header[] header = httpResponse.getHeaders("Set-Cookie");
						map.put("ASP.NET_SessionId", header[0].getValue()
								.trim().split(";")[0].split("=")[1]);
						
						
						// ��ʼ��html�л�ȡ��Ҫpost��Ԫ��
						HttpEntity entity = httpResponse.getEntity();
						String html = EntityUtils.toString(entity, "utf-8");
						Document doc = Jsoup.parse(html);  //ʹ��Jsoup����н���
						String VIEWSTATE = doc.select("[name=__VIEWSTATE]") //��ȡnameΪ��__VIEWSTATE��������
								.attr("value").toString();
						map.put("_VIEWSTATE", VIEWSTATE);     //����ȡ����������ӽ�map����
						String VIEWSTATEGENERATOR = doc
								.select("[name=__VIEWSTATEGENERATOR]")
								.attr("value").toString();
						map.put("_VIEWSTATEGENERATOR", VIEWSTATEGENERATOR);
						String POST = doc.select("[name=Button1]")
								.attr("value").toString();
						String Radio = doc.select("[name=RadioButtonList1]")
								.attr("value").toString();
						map.put("Button1", POST);
						map.put("Radio", Radio);
						
						
						// ��ʼpost
						post();
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

	
	/***
	 * �������Post��¼���ݣ�
	 * Post��ַ��http://jwc.hlu.edu.cn/zfxk/default_zjjm.aspx
	 * ��Ҫ�������POST�������ݣ�
	 * __VIEWSTATE
	 * __VIEWSTATEGENERATOR
	 * yh  ���û���
	 * kl  ������
	 * RadioButtonList1
	 * Button1
	 * 
	 * ��Ҫ��http�ײ���ӣ�
	 * Origin �� http://jwc.hlu.edu.cn
	 * Referer ��http://jwc.hlu.edu.cn/zfxk/
	 */
	private void post() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://jwc.hlu.edu.cn/zfxk/default_zjjm.aspx");
		httpPost.addHeader("Cookie",
				"ASPSESSIONIDQQTSASRQ=" + map.get("ASPSESSIONIDQQTSASRQ") + ";"
						+ " ASP.NET_SessionId=" + map.get("ASP.NET_SessionId"));
		httpPost.addHeader("Origin", "http://jwc.hlu.edu.cn");
		httpPost.addHeader("Referer", "http://jwc.hlu.edu.cn/zfxk/");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE", map.get("_VIEWSTATE")));
		params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", map
				.get("_VIEWSTATEGENERATOR")));
		params.add(new BasicNameValuePair("yh", accountStr));
		params.add(new BasicNameValuePair("kl", passwordStr));
		params.add(new BasicNameValuePair("RadioButtonList1", map.get("Radio")));
		params.add(new BasicNameValuePair("Button1", map.get("Button1")));
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				openUserHome();    //�򿪸�����ҳ
				// ��ȡ�ɼ�
				getGrade();
				

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �򿪽��񴦸�����ҳ �� GET  ��  http://jwc.hlu.edu.cn/zfxk/default3.aspx?novell_yhm=ѧ��&novell_mm=����&novell_js=XS
	 */
	private void openUserHome() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://jwc.hlu.edu.cn/zfxk/default3.aspx?novell_yhm=" + accountStr + "&novell_mm=" + passwordStr + "&novell_js=XS");
		httpGet.addHeader(
				"Cookie",
				"ASPSESSIONIDQQTSASRQ="
						+ map.get("ASPSESSIONIDQQTSASRQ") + "; "
						+ "ASP.NET_SessionId="
						+ map.get("ASP.NET_SessionId"));
		HttpResponse httpResponseUser;
		try {
			httpResponseUser = httpClient.execute(httpGet);
			Log.d("Header", String.valueOf(httpResponseUser.getStatusLine()
					.getStatusCode()));

			Log.d("Header", "ok");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/** 
	 * ��ȡ�ɼ� : GET��  http://jwc.hlu.edu.cn/zfxk/xscj.aspx?xh=ѧ��
	 */
	private void getGrade(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGetGrade = new HttpGet(
				"http://jwc.hlu.edu.cn/zfxk/xscj.aspx?xh=" + accountStr);
		httpGetGrade.addHeader(
				"Cookie",
				"ASPSESSIONIDQQTSASRQ="
						+ map.get("ASPSESSIONIDQQTSASRQ") + "; "
						+ "ASP.NET_SessionId="
						+ map.get("ASP.NET_SessionId"));
		httpGetGrade.addHeader("Host", "jwc.hlu.edu.cn");
		httpGetGrade.addHeader("Referer",
				"http://jwc.hlu.edu.cn/zfxk/xsleft.aspx?flag=xxcx");
		HttpResponse httpResponseGrade;
		try {
			httpResponseGrade = httpClient.execute(httpGetGrade);
			if(httpResponseGrade.getStatusLine().getStatusCode() == 200){
				HttpEntity entityGrade = httpResponseGrade.getEntity();
				String responseGrade = EntityUtils.toString(entityGrade, "utf-8");
				Document doc = Jsoup.parse(responseGrade);
				Element e = doc.getElementById("DataGrid1");
				
				Elements es = e.select("td");
				extractGrade(es);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void extractGrade(Elements array){
		
		
		int i = 17;
		for(; i < array.size();i+=15){
			Grade grade = new Grade();
			grade.setCourseName(array.get(i).text().toString());
			grade.setTeacher(array.get(i+2).text().toString());  //i=20
			grade.setUsuallyResults(array.get(i+4).text().toString()); //i=22
			grade.setFinalGrade(array.get(i+5).text().toString());  //i=23
			grade.setSumGrade(array.get(i+6).text().toString());  //i=24
			grade.setGPA(array.get(i+10).text().toString());  //i=28
			grade.setCredit(array.get(i+11).text().toString());  //i=29
			grade.setCreditGPA(array.get(i+12).text().toString()); //i=30
			list.add(grade);

			Log.d("TD", grade.getCourseName());
			Log.d("TD", grade.getTeacher());
			Log.d("TD", grade.getUsuallyResults());
			Log.d("TD", grade.getFinalGrade());
			Log.d("TD", grade.getSumGrade());
			Log.d("TD", grade.getGPA());
			Log.d("TD", grade.getCredit());
			Log.d("TD", grade.getCreditGPA());
		}
		Message message = new Message();
		message.what = 1;
		handler.sendMessage(message);
		
	}
	
	

}
