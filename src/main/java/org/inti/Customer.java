package org.inti;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.inti.model.DrinkType;
import org.inti.model.Order;
import org.springframework.beans.factory.InitializingBean;

public class Customer extends RouteBuilder implements InitializingBean, CamelContextAware {
	private CamelContext camelContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if (camelContext == null) {
			// this.addRoutesToCamelContext(camelContext);
			camelContext.addRoutes(this);
		}

	}

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("timer:myTimer?fixedRate=true&period=10000")

				.process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {

						Order order = new Order();
						List<OrderItem> lo = new ArrayList<>();
						order.setNumber(1);
//						order.setListOrderItems(lo);
						order.additem(DrinkType.ESPRESSO, 5, true);
						order.additem(DrinkType.MOCHA, 8, false);
						exchange.getIn().setBody(order);
						
//						List<OrderItem> liste = order.getListOrderItems();
//				for (OrderItem orderItem : liste) {
//					System.out.println(orderItem.getDrinkType().toString());
//				}

					}
				}).to("direct:cafe");

	}

	public CamelContext getCamelContext() {
		return camelContext;
	}

	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = camelContext;
	}

}
