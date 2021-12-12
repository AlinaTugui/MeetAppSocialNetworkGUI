package socialnetwork.service;

import socialnetwork.domain.Tuple;

public class ServiceCereriPrietenie {
    private ServiceCereri srvCereri;
    private ServicePrietenie srvPrietenie;

    public ServiceCereriPrietenie(ServiceCereri srvCereri, ServicePrietenie srvPrietenie) {
        this.srvCereri = srvCereri;
        this.srvPrietenie = srvPrietenie;
    }
    public void stergePrietenie(Long id1, Long id2) {
        srvPrietenie.deletePrietenie(id1,id2);
        srvCereri.delete(id1, id2);
    }
}
