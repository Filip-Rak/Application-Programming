package pau.pau5.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pau.pau5.classEmployee.ClassEmployee;
import pau.pau5.classEmployee.ClassEmployeeRepository;

@Service
public class RateService
{
    // Attributes
    private final RateRepository rateRepository;
    private final ClassEmployeeRepository classEmployeeRepository;

    // Constructor
    @Autowired
    public RateService(RateRepository rateRepository, ClassEmployeeRepository classEmployeeRepository)
    {
        this.rateRepository = rateRepository;
        this.classEmployeeRepository = classEmployeeRepository;
    }

    // Methods
    public Rate addRate(RateDTO rateDTO)
    {
        try
        {
            ClassEmployee classEmployee = classEmployeeRepository.findById(rateDTO.getClassEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found"));

            Rate rate = new Rate(rateDTO.getRating(), classEmployee, rateDTO.getComment());
            return rateRepository.save(rate);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Invalid rating: " + e.getMessage());
        }
    }
}
