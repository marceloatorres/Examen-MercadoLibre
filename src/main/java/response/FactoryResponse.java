package response;

import model.Ip;
import utils.TypeResponse;

import java.util.List;

import model.Country;
public class FactoryResponse {

	@SuppressWarnings("unchecked")
	public IResponse getObjectResponse(TypeResponse type, boolean success, String message, Object obj) {
		switch(type){
		case IP:
			return new ResponseIp(success, message,(Ip)obj);
		case STATISTIC:
			return new ResponseStatistic(success, message,(List<Country>) obj);
		case COUNTRY:
			return new ResponseCountry(success, message,(Country) obj);
		case LONG:
			return new ResponseLong(success, message,(long) obj);
		}
		
		return null;
	}
}
