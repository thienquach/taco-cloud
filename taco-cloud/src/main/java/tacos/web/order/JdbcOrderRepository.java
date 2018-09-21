//package tacos.web.order;
//
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import tacos.web.taco.Taco;
//
//@Repository
//public class JdbcOrderRepository implements OrderRepository{
//
//	private SimpleJdbcInsert orderInserter;
//	private SimpleJdbcInsert orderTacoInserter;
//	private ObjectMapper objectMapper;
//	
//	@Autowired
//	public JdbcOrderRepository(JdbcTemplate jdbc) {
//		this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
//		this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
//		this.objectMapper = new ObjectMapper();
//	}
//	
//	@Override
//	public Order save(Order order) {
//		long orderId = saveOrderDetails(order);
//		order.setId(orderId);
//		
//		order.getTacos().stream().forEach(taco -> saveTacoToOrder(taco, orderId));
//		
//		return order;
//	}
//
//	private long saveOrderDetails(Order order) {
//		@SuppressWarnings("unchecked")
//		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
//		values.put("placedAt", new Date());
//		
//		return orderInserter.executeAndReturnKey(values).longValue();
//	}
//
//	private void saveTacoToOrder(Taco taco, long orderId) {
//		Map<String, Object> values = new HashMap<>();
//		values.put("tacoOrder", orderId);
//		values.put("taco", taco.getId());
//		orderTacoInserter.execute(values);
//	}
//}
