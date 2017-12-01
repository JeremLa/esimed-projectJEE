package controller.chart;

import dao.BillDAO;
import entity.User;
import org.primefaces.model.chart.MeterGaugeChartModel;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class GaugeChartController implements Serializable{
    @Inject
    private BillDAO billDAO;
    @Inject
    private User user;
    private MeterGaugeChartModel meterGaugeModel1;
    private Double CA;

    @PostConstruct
    public void init() {
        CA = billDAO.getCurrentYearCA(user, 2017);
        createMeterGaugeModels();
    }

    private void createMeterGaugeModels() {
        meterGaugeModel1 = initMeterGaugeModel();
        meterGaugeModel1.setTitle("CA / plafond");
        meterGaugeModel1.setSeriesColors("E7E658, 93b75f, 66cc66, ff6666");
        meterGaugeModel1.setGaugeLabel("~" + CA.intValue());
        meterGaugeModel1.setLabelHeightAdjust(110);
    }

    private MeterGaugeChartModel initMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>(){{
            add(user.getCAMax() * 0.2);
            add(user.getCAMax() * 0.5);
            add(user.getCAMax());
            add(user.getCAMax() + user.getCAMax() * 0.4);
        }};

        Number display = CA;
        Number max = user.getCAMax() + user.getCAMax() * 0.4;

        if(display.intValue() > max.intValue()){
            display = max;
        }

        return new MeterGaugeChartModel(display, intervals, intervals);
    }

    public MeterGaugeChartModel getMeterGaugeModel1() {
        return meterGaugeModel1;
    }

    public void setMeterGaugeModel1(MeterGaugeChartModel meterGaugeModel1) {
        this.meterGaugeModel1 = meterGaugeModel1;
    }
}
