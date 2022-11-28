package luongdev.switchconfig.cluster.queries.handlers;

import luongdev.switchconfig.cluster.Instance;
import luongdev.switchconfig.cluster.Instances;
import luongdev.switchconfig.cluster.queries.GetAllInstancesQuery;
import luongld.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllInstancesHandler implements RequestHandler<List<Instance>, GetAllInstancesQuery> {

    private final Instances instances;

    public GetAllInstancesHandler(Instances instances) {
        this.instances = instances;
    }

    @Override
    public List<Instance> handle(GetAllInstancesQuery ignored) {
        return instances.findAll();
    }
}
