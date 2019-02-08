package rs.ftn.isa;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.repository.CenovnikRentRepository;
import rs.ftn.isa.repository.RentACarRepository;
import rs.ftn.isa.repository.UslugaRepository;
import rs.ftn.isa.service.CenovnikRentService;
import rs.ftn.isa.service.CenovnikRentServiceImpl;
import rs.ftn.isa.service.FilijalaService;
import rs.ftn.isa.service.HotelService;
import rs.ftn.isa.service.HotelServiceImpl;
import rs.ftn.isa.service.RentACarService;
import rs.ftn.isa.service.RoomService;
import rs.ftn.isa.controller.FightController;

import rs.ftn.isa.service.RoomServiceImp;
import rs.ftn.isa.service.UslugaService;
import rs.ftn.isa.service.UslugaServiceImpl;
import rs.ftn.isa.service.VoziloService;
import rs.ftn.isa.service.VoziloServiceImpl;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.PricelistHotel;
import rs.ftn.isa.model.*;
import rs.ftn.isa.repository.*;
import rs.ftn.isa.service.*;
import rs.ftn.isa.controller.*;

@EnableTransactionManagement
@SpringBootApplication
public class IsaApplication {

	public static void main(String[] args) {

		 SpringApplication.run(IsaApplication.class, args);
	}
		
}

