package com.oye.ratingsystem.service;

import com.oye.ratingsystem.contract.RatingContract;
import com.oye.ratingsystem.model.Driver;
import com.oye.ratingsystem.model.Passenger;
import com.oye.ratingsystem.repository.DriverRepository;
import com.oye.ratingsystem.repository.PassengerRepository;
import com.oye.ratingsystem.repository.PersonRepository;
import com.oye.ratingsystem.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements RatingContract.Service {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    @Qualifier("responseRateDTO")
    private RatingContract.Dto.ResponseRate responseRateDTO;

    @Autowired
    @Qualifier("responseAvgRateDTO")
    private RatingContract.Dto.ResponseAvgRate responseAvgRateDTO;

    @Override
    public Boolean setDriverRating(RatingContract.Dto.RequestRate request) {
        if(!rideRepository.existsById(request.getRideId()))
            return false;
        setDriverAvgRating(request);
        return  rideRepository.updateDriverRating(request.getRideId(), request.getRate()) == 1;
    }

    private void setDriverAvgRating(RatingContract.Dto.RequestRate request) {
        Driver driver = rideRepository.getOne(request.getRideId()).getDriver();
        Float oldAvgRating = driver.getAvgRating();
        Integer oldCount = driver.getRatingCount();
        Float newAvgRating = 0f;
        Float oldRating = rideRepository.getOne(request.getRideId()).getDriverRate();
        Float newRating = request.getRate();

        if(oldRating != 0f && oldCount != 0){
            // already rated before so remove previous rating from AvgRating and try to rate again for same Ride
            newAvgRating = oldAvgRating + (newRating-oldRating)/oldCount;
        }
        else {
            //normal case
            newAvgRating = ((oldAvgRating*oldCount) + newRating)/(oldCount+1);
            driver.setRatingCount(oldCount+1);
        }
        driver.setAvgRating(newAvgRating);
        driverRepository.save(driver);
    }

    private float findPreviousAvgRating(Float oldAvgRating, Float oldRating) {
        return (oldAvgRating * 2) - oldRating;
    }


    @Override
    public RatingContract.Dto.ResponseRate getResponseRate(Boolean status) {
            if (Boolean.TRUE == status) {
                responseRateDTO.setStatus(RatingContract.Dto.ResponseRate.STATUS_SUCCESSFUL);
                responseRateDTO.setMessage(RatingContract.Dto.ResponseRate.RATING_UPDATED);
            } else {
                responseRateDTO.setStatus(RatingContract.Dto.ResponseRate.STATUS_FAILED);
                responseRateDTO.setMessage(RatingContract.Dto.ResponseRate.RIDE_NOT_FOUND);
            }
            return responseRateDTO;
    }

    @Override
    public Boolean setPassengerRating(RatingContract.Dto.RequestRate request) {
        if(!rideRepository.existsById(request.getRideId()))
            return false;
        setPassengerAvgRating(request);
        return  rideRepository.updatePassengerRating(request.getRideId(), request.getRate()) == 1;
    }
    private void setPassengerAvgRating(RatingContract.Dto.RequestRate request) {
        Passenger passenger = rideRepository.getOne(request.getRideId()).getPassenger();
        Float oldAvgRating = passenger.getAvgRating();
        Integer oldCount = passenger.getRatingCount();
        Float newAvgRating = 0f;
        Float oldRating = rideRepository.getOne(request.getRideId()).getPassengerRate();
        Float newRating = request.getRate();

        if(oldRating != 0f && oldCount != 0){
            // already rated before so remove previous rating from AvgRating and try to rate again for same Ride
            newAvgRating = oldAvgRating + (newRating-oldRating)/oldCount;
        }
        else {
            //normal case
            newAvgRating = ((oldAvgRating*oldCount) + newRating)/(oldCount+1);
            passenger.setRatingCount(oldCount+1);
        }
        passenger.setAvgRating(newAvgRating);
        passengerRepository.save(passenger);
    }

    @Override
    public RatingContract.Dto.ResponseAvgRate getResponseDriverAvgRating(Integer driverId) {
        synchronized (responseAvgRateDTO){
        if(!driverRepository.existsById(driverId)){
            responseAvgRateDTO.setAvgRating(-1F);
            responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.USER_NOT_FOUND);
            return responseAvgRateDTO;
        }
        else if(driverRepository.existsById(driverId) && driverRepository.getOne(driverId).getAvgRating() == 0){
            responseAvgRateDTO.setAvgRating(-1F);
            responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.RATING_NOT_FOUND);
            return responseAvgRateDTO;
        }

        Float avgRating = driverRepository.getOne(driverId).getAvgRating();
        responseAvgRateDTO.setAvgRating(avgRating);
        responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.MSG_SUCCESSFUL);

        return responseAvgRateDTO;
        }
    }

    @Override
    public RatingContract.Dto.ResponseAvgRate getResponsePassengerAvgRating(Integer passengerId) {
        synchronized (responseAvgRateDTO){
        if(!passengerRepository.existsById(passengerId)){
            responseAvgRateDTO.setAvgRating(-1F);
            responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.USER_NOT_FOUND);
            return responseAvgRateDTO;
        }
        else if(passengerRepository.existsById(passengerId) && passengerRepository.getOne(passengerId).getAvgRating() == 0){
            responseAvgRateDTO.setAvgRating(-1F);
            responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.RATING_NOT_FOUND);
            return responseAvgRateDTO;
        }

        Float avgRating = passengerRepository.getOne(passengerId).getAvgRating();
        responseAvgRateDTO.setAvgRating(avgRating);
        responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.MSG_SUCCESSFUL);

        return responseAvgRateDTO;
        }
    }
}
