package com.cg.proxyim.Controller;

import com.cg.proxyim.Entity.DatumElement;
import com.cg.proxyim.Entity.ErrorDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class FetchErrorDetailsController {
    @GetMapping("/api/charts/error-details")
    @ResponseBody
    public ErrorDetails fetchError(@RequestParam(required = false) String event_name,@RequestParam(required = false) String time_interval) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(200);
        DatumElement datumElement = new DatumElement();
        datumElement.setErrorMsg("接口错误");
        datumElement.setErrorType("Apierror");
        datumElement.setErrorReason("编不出来异常归因");
        ArrayList<DatumElement> datumElements = new ArrayList<>();
        datumElements.add(datumElement);
        errorDetails.setData(datumElements);
        return errorDetails;
    }
}
