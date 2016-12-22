import gov.mt.ufms.UfmsProto;
import gov.mt.ufms.UfmsProto.RegVehicle.VehicleType;

import java.io.FileOutputStream;
import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestUfms {

	public static void main(String[] args) throws Exception {
		// AMQP
		ConnectionFactory factory = new ConnectionFactory();
//		factory.setHost("210.211.102.123");
//		factory.setUsername("test");
//		factory.setPassword("test");
		
		factory.setHost("27.118.27.118");		
		factory.setUsername("ctpmdktest");
		factory.setPassword("JQem79_XM");
		factory.setPort(5675);
		
		factory.setConnectionTimeout(600);
		
		
		
		

		Connection connection = factory.newConnection();		
		System.out.println("creat connection done!");		
		Channel channel = connection.createChannel();
		System.out.println("creat channel done!");
		
		
		
		UfmsProto.RegCompany.Builder builderRegCompany = UfmsProto.RegCompany.newBuilder();
		builderRegCompany.setAddress("11-chaulong");
		builderRegCompany.setCompany("CPR_0101885631");
		builderRegCompany.setName("cadpro");
		builderRegCompany.setTel("0437152984");		
		UfmsProto.RegCompany rc = builderRegCompany.build();
		UfmsProto.BaseMessage rcMsg = UfmsProto.BaseMessage.newBuilder().setMsgType(UfmsProto.BaseMessage.MsgType.RegCompany).setExtension(UfmsProto.RegCompany.msg, rc).build();		
		byte[] rcData = rcMsg.toByteArray();
		System.out.println(rcMsg.toString());
		
		
		UfmsProto.RegDriver.Builder builderRegDriver = UfmsProto.RegDriver.newBuilder();
		builderRegDriver.setDatetimeExpire(1981979234);
		builderRegDriver.setDatetimeIssue(1095379200);
		builderRegDriver.setDriver("CPR_010099045659");
		builderRegDriver.setLicense("B2");
		builderRegDriver.setName("Dao Duy Hung");
		builderRegDriver.setRegPlace("Ha Noi");
		UfmsProto.RegDriver rd = builderRegDriver.build();
		UfmsProto.BaseMessage rdMsg = UfmsProto.BaseMessage.newBuilder().setMsgType(UfmsProto.BaseMessage.MsgType.RegDriver).setExtension(UfmsProto.RegDriver.msg, rd).build();		
		byte[] rdData = rdMsg.toByteArray();
		System.out.println(rdMsg.toString());
		
		UfmsProto.RegVehicle.Builder builderRegVehicle = UfmsProto.RegVehicle.newBuilder();
		builderRegVehicle.setCompany("CPR_0101885631");
		builderRegVehicle.setDatetime(1481979234);
		builderRegVehicle.setDeviceId("CPR_01");
		builderRegVehicle.setDeviceModel("CPR_Model_1");
		builderRegVehicle.setDeviceModelNo(1);
		builderRegVehicle.setDriver("CPR_010099045659");
		builderRegVehicle.setSim("0966871864");
		builderRegVehicle.setVehicle("29A1511");
		builderRegVehicle.setVehicleType(VehicleType.HopDong);
		builderRegVehicle.setVin("01");
		UfmsProto.RegVehicle rv = builderRegVehicle.build();
		UfmsProto.BaseMessage rvMsg = UfmsProto.BaseMessage.newBuilder().setMsgType(UfmsProto.BaseMessage.MsgType.RegVehicle).setExtension(UfmsProto.RegVehicle.msg, rv).build();		
		byte[] rvData = rvMsg.toByteArray();
		System.out.println(rvMsg.toString());
		
		
		
		// build WayPoint message 
		UfmsProto.WayPoint.Builder builder = UfmsProto.WayPoint.newBuilder();
//		builder.setDatetime(1234);
//		builder.setDoor(false);
//		builder.setDriver("1235");
//		builder.setHeading(90);
//		builder.setIgnition(true);
//		builder.setSpeed(90);
//		builder.setVehicle("ABCD");
//		builder.setX(100);
//		builder.setY(50);
//		builder.setZ(20);
		builder.setDatetime(1481981234);
		builder.setDoor(false);
		builder.setDriver("CPR_010099045659");
		builder.setHeading(90);
		builder.setIgnition(true);
		builder.setSpeed(90);
		builder.setVehicle("29A1511");
		builder.setX(100);
		builder.setY(50);
		builder.setZ(20);

		UfmsProto.WayPoint wp = builder.build();
		UfmsProto.BaseMessage msg = UfmsProto.BaseMessage.newBuilder().setMsgType(UfmsProto.BaseMessage.MsgType.WayPoint).setExtension(UfmsProto.WayPoint.msg, wp).build();		
		byte[] data = msg.toByteArray();
		System.out.println(msg.toString());
		
//		msg.writeTo(new FileOutputStream("C:/work/eposi/ufms/ufms-proto/test/WayPoint.msg"));
		
		// send to server
		//channel.basicPublish("ufms.all", "", null, data);
		channel.basicPublish("tracking.ctpmdktest", "track1", null, rcData);
		channel.basicPublish("tracking.ctpmdktest", "track1", null, rdData);
		channel.basicPublish("tracking.ctpmdktest", "track1", null, rvData);		
		channel.basicPublish("tracking.ctpmdktest", "track1", null, data);
		System.out.println("send done! ");

		// clean up
		channel.close();
		connection.close();
	}

}
