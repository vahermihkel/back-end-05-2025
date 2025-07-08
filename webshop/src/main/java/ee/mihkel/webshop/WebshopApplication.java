package ee.mihkel.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}

}

// 4.T 06.05 - andmebaas, kontroller
// 5.N 08.05 14.00
// 6.T 13.05 14.00
// 7.N 15.05 14.00
// 8.E 19.05 17.30
// 9.R 23.05 09.00 --> autentimine
//11.E 26.05 14.00 --> autentimine
//10.K 28.05 14.00
//12.K 04.06 14.00 --> autentimine
//13.R 13.06 14.00-16.15    3ak/h
//14.E 16.06 9.00
//15a.N 19.06 9.00-11.15
//15b.K 02.07 8.30-10.00 (poolik)

//Front-end - React
//Autentimine
// TypeScript
//Makse (EveryPay) - lihtsam ülevaade
// Filtreerimine kategooria järgi
//Meilide saatmine
// Logisid
// Keskkonnad: testkeskkond+live keskkond

// Cache

// Unit Testid

// 60ak/h / 4ak/h =  15päeva
