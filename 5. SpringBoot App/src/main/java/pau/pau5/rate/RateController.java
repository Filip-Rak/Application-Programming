package pau.pau5.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/rating")
public class RateController
{
    // Attributes
    private final RateService rateService;

    // Constructor
    @Autowired
    public RateController(RateService rateService) { this.rateService = rateService; }

    // Methods
    @PostMapping
    public ResponseEntity<?> addRate(@RequestBody RateDTO rateDTO)
    {
        try
        {
            Rate rate = rateService.addRate(rateDTO);
            return new ResponseEntity<>(rate, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
