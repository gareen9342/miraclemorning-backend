package com.miraclemorning.service;

import com.miraclemorning.domain.Team;
import com.miraclemorning.repository.TeamRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    @Autowired
    private final TeamRepository teamRepository;

    public void register(Team team){
        teamRepository.save(team);
    }
}
