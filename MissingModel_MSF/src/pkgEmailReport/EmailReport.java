/*MODIFICATION LOG :*/
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/*	UTILITY NAME			:	UK_PRODUCTIMAGE_CHECK
 * 	CLASS NAME				:	EMAILREPORT.JAVA
 *	ROLE OF CLASS			:	THIS CLASS SENDS THE EMAIL REPORT WITH OUTPUT EXCEL FILE AS AN ATTACHMENT.   
 *	AUTHOR					:	RITESH PHAKATKAR
 *	CODED ON				:	19.07.2017
 *	UTILITY REQUESTED BY 	:	FAIZAL SAYANI	
 *	REVIEWED BY				:	RAHUL CHOUGULE
 *	INPUT					:	OUTPUT FILE, RECEIPENT LIST
 *	OUTPUT					:	EMAIL TO ALL THE PEOPLE IN RECEPIENT LIST WITH OUTPUT EXCEL FILE 
 */
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/

/*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/* 	LAST UPDATED DATE		:
 * 	UPDATED BY				:
 * 	CHANGE REQUESTED BY		:
 * 	CHANGES MADE			:
 */
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/


package pkgEmailReport;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import pkgreadfiles.ReadFiles;
import pkgtestmethods.TestCase1;
import pkgutil.ProductPage;


public class EmailReport {
	
	Session session;
	Properties props;
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String hostname = "Unknown";
	StringBuilder sb = new StringBuilder();
	String bodyStart="<html><body>";
	String tableStart="<table border=2>";
	String rowStart="<tr>";
	String rowEnd="</tr>";
	String columnStart="<td>";
	String columnEnd="</td>";
	String tableEnd="</table>";
	String bodyEnd="</body></html>";
	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	String defaultCC="Suwarna_Sapre@next.co.uk";
	 
	
	public EmailReport() {
		try
		{
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{

		}
		props= System.getProperties();//new Properties();
		System.setProperty("java.net.preferIPv4Stack" , "true");

		
		props.setProperty("mail.smtp.auth", "false");		
		//props.put("mail.smtp.host", "10.154.96.247");
		//props.put("mail.smtp.ssl.trust", "10.154.96.247");
		props.put("mail.smtp.host", "smtpmail1.next-uk.next.loc");
		props.setProperty("mail.smtp.starttls.enable","true");
		
		session = Session.getInstance(props,null);
	}
	
	public void sendReport(String strTo,String strCc,String strFilePath,String strFileName){

		if(strFileName.equalsIgnoreCase("")){
			try{
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("PuneSeleniumAutomationTeam@capita.co.uk"));
				message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(""));
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(defaultCC));

				message.setSubject("Model Images Missing data on Missselfridge");
				message.setText("Model Images Missing data on Missselfridge"+hostname);

				Transport.send(message);

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		else{
			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("PuneSeleniumAutomationTeam@capita.co.uk"));
				message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(""));
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(defaultCC));

				message.setSubject("Model Images Missing data on Missselfridge");
				message.setText("PFA");

				MimeBodyPart messageBodyPart = new MimeBodyPart();
				Multipart multipart = new MimeMultipart();

				//Add Message Body
				messageBodyPart = new MimeBodyPart();			
				messageBodyPart.setContent(createMsgBody().toString(), "text/html");
				multipart.addBodyPart(messageBodyPart);			
				// Add Attachment
				messageBodyPart = new MimeBodyPart();			
				DataSource sourceReport = new FileDataSource(strFilePath);
				messageBodyPart.setDataHandler(new DataHandler(sourceReport));
				messageBodyPart.setFileName(strFileName);
				multipart.addBodyPart(messageBodyPart);	

				message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(strTo));
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(strCc+","+defaultCC));
				
				message.setContent(multipart);

				Transport.send(message);

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public StringBuilder createMsgBody(){

		long eTime=System.currentTimeMillis();
		long difference = (long) ((eTime - TestCase1.sTime)/1000f);
		long hr=difference/3600;
		long min=(difference/60)%60;
		long sec=difference%60;
		String tempstr="";
		for(int i=0;i<ReadFiles.Category.size();i++){
			if (i==0){
				tempstr = ReadFiles.Category.get(i);
			}
			else{
				tempstr = tempstr + ","  + ReadFiles.Category.get(i);
			}
		}
		
		sb.append(bodyStart+"Hi All, <br><br> This E-mail contain the test result for Model Image Check on Live <br><br>"+tableStart
				+rowStart+columnStart+"<h3>Execution Started At </h3>"+columnEnd+columnStart+(dateFormat.format(ReadFiles.sTime))+columnEnd+rowEnd
				+rowStart+columnStart+"<h3>EndTime</h3>"+columnEnd+columnStart+(dateFormat.format(eTime))+columnEnd+rowEnd
				+rowStart+columnStart+"<h3>Total Execution Time</h3>"+columnEnd+columnStart+(hr+" Hr: "+min+" Min: "+sec+" Seconds")+columnEnd+rowEnd
				+rowStart+columnStart+"<h3>Total Links Tested</h3>"+columnEnd+columnStart+ReadFiles.ProductlistURL.size()+columnEnd+rowEnd
				+rowStart+columnStart+"<h3>Enviornment</h3>"+columnEnd+columnStart+ReadFiles.enviornment+columnEnd+rowEnd
				+rowStart+columnStart+"<h3>Category</h3>"+columnEnd+columnStart+tempstr+columnEnd+rowEnd+tableEnd
				+"<br><b>***This is auto generated email. Please do not reply***"+bodyEnd);
		return sb;
	}




}
