package gr.kariera.mindthecode.web.clients;


import gr.kariera.mindthecode.shared.CarController;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("rest")
public interface CarClient extends CarController { }