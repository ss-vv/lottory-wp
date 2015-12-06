package com.unison.lottery;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 集成测试用的发送HTTP
 * @author Yang Bo
 *
 */
public class HttpPostTester {

	private String postContent;
	private HttpClient httpClient;
	private String url;
	
	public HttpPostTester(String url, String postContent){
		this.postContent = postContent;
		this.httpClient = new DefaultHttpClient();
		this.url = url;
	}
	
	public String post() throws ClientProtocolException, IOException{
		System.out.println("url: " + url);
		System.out.println("post content: \n" + postContent);
		return postToService();
	}

    private class DefaultResponseHandler implements ResponseHandler<String> {
        private Header[] headers;

		@Override
        public String handleResponse(org.apache.http.HttpResponse response) throws ClientProtocolException, IOException {
            String chaset = EntityUtils.getContentCharSet(response.getEntity());
            if (chaset == null) {
                chaset = HTTP.UTF_8;
            }
            headers = response.getAllHeaders();
            return EntityUtils.toString(response.getEntity(), chaset);
        }

		public Header[] getHeaders() {
			return headers;
		}
    };

	private String postToService() throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(postContent, "utf-8");
		post.setEntity(entity);
		
		DefaultResponseHandler handler = new DefaultResponseHandler();
		String resp = httpClient.execute(post, handler);
		printHeader(handler.getHeaders());
		return resp;
	}

	private void printHeader(Header[] headers) {
		for (Header header : headers){
			System.out.println(header.getName() + " : " + header.getValue());
		}
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		String url = "http://www.davcai.com/rcv/order_result.do";
		String content = "transcode=007&msg=<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"007\" partnerid=\"349040\" version=\"1.0\" time=\"20120830152237\" /><body><ticketresults><ticketresult lotteryId=\"JCSPF\" ticketId=\"319988\" palmId=\"6408403\" statusCode=\"003\" message=\"交易成功\" printodd=\"4-001:[胜=1.38]/4-002:[胜=1.72]\" Unprintodd=\"1.38;1.72\" printNo=\"968F-257BE6FEE3E8\" maxBonus=\"4.75\" /></ticketresults></body></msg>&key=BDA61EFB62C8B9514DE8A2277ADEE7B1&partnerid=349040";
		HttpPostTester postTester = new HttpPostTester(url, content);
		String ret = postTester.post();
		System.out.println("Resp: \n" + ret);
	}
}
