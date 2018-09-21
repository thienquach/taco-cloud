package tacos.web.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findByDeliveryZip(String deliveryZip);
	List<Order> findByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
}
