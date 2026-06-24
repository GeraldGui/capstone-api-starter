package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    public Profile getProfileService(int userId) {
        return profileRepository.findById(userId).orElseThrow();
    }

    public Profile updateProfileService(int userId, Profile profile) {
        Profile exist = profileRepository.findById(userId).orElseThrow();

        exist.setFirstName(profile.getFirstName());
        exist.setLastName(profile.getLastName());
        exist.setPhone(profile.getPhone());
        exist.setEmail(profile.getEmail());
        exist.setAddress(profile.getAddress());
        exist.setCity(profile.getCity());
        exist.setState(profile.getState());
        exist.setZip(profile.getZip());

        profileRepository.save(exist);
        return profileRepository.findById(userId).orElseThrow();
    }
}
