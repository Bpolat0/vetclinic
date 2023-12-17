package dev.patika.vetclinic.api;

import dev.patika.vetclinic.business.abstracts.IAvailableDateService;
import dev.patika.vetclinic.core.config.modelMapper.IModelMapperService;
import dev.patika.vetclinic.core.result.Result;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.vetclinic.dto.request.availabledate.AvailableDateUpdateRequest;
import dev.patika.vetclinic.dto.response.CursorResponse;
import dev.patika.vetclinic.dto.response.availabledate.AvailableDateResponse;
import dev.patika.vetclinic.entities.AvailableDate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/available-dates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapper;

    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapper) {
        // This constructor provides us to access the methods of the AvailableDateService class.
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        // This method saves the available date.
        AvailableDate saveAvailableDate = this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class);
        this.availableDateService.save(saveAvailableDate);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAvailableDate, AvailableDateResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") Long id) {
        // This method gets the available date by id.
        AvailableDate availableDate = this.availableDateService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            // This method returns the available dates with pagination.
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        Page<AvailableDate> availableDates = this.availableDateService.cursor(page, size);
        Page<AvailableDateResponse> availableDateResponsePage = availableDates
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));

        return ResultHelper.cursor(availableDateResponsePage);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        // Map the request to an AvailableDate object
        AvailableDate availableDate = this.modelMapper.forRequest().map(availableDateUpdateRequest, AvailableDate.class);

        // Call the update method in the AvailableDateManager class
        AvailableDate updatedAvailableDate = this.availableDateService.update(availableDate);

        // Map the updated available date to an AvailableDateResponse object
        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(updatedAvailableDate, AvailableDateResponse.class);

        // Return the updated available date
        return ResultHelper.success(availableDateResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        // This method deletes the available date by id.
        this.availableDateService.delete(id);
        return ResultHelper.ok();
    }
}