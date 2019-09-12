package com.dtalavera.ejerciciosoa.crudaws.services;

@org.springframework.stereotype.Service
public class ServiceAll{
	
	private ServiceRN srn = new ServiceRN();
	private ServiceEloqua sel = new ServiceEloqua();
	private ServiceOS sos = new ServiceOS();
	
	
	public String deleteFromAll(String email) {
		
		String respuestaRN = srn.deleteRNContact(email);
		String respuestaEl = sel.deleteElContact(email);
		String respuestaOS = sos.deleteOSContact(email);

		return "Right Now: \n\t" + respuestaRN + "\nEloqua: \n\t" + respuestaEl + "\nOracle Sales Cloud: \n\t" + respuestaOS;
	}
	
	
	public String createAtAll(String json) {

		String respuestaEl = sel.serializarObjecto(json);
		String respuestaRN = srn.serializarObjecto(json);
		String respuestaOS = sos.serializarObjectoContact(json);
		
		return "Right Now: \n\t" + respuestaRN + "\nEloqua: \n\t" + respuestaEl + "\nOracle Sales Cloud: \n\t" + respuestaOS;
	}

}
