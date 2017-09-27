package com.sso.flow.server.impl.repositories;

import com.sso.common.server.model.entities.Position;
import com.sso.common.server.model.repositories.PositionRepository;
import com.sso.flow.server.GenericUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Original Author: Alexander Kontarero
 * @version 2/19/2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class PositionRepositoryUnitTest extends GenericUnitTest {
    @Autowired
    PositionRepository positionRepository;

    @Test
    public void shouldCreateNewPositionRecordForManualSequenceIdAccessSuccessfully(){
        long originalPositionRecordNumber = positionRepository.count();

        Position position = new Position();
        Long positionId = positionRepository.getNextSequenceId();
        position.setId(positionId);
        position.setName("Qwerty");

        positionRepository.save(position);

        assertNotNull(position.getId());
        assertThat(positionRepository.count(), is(++originalPositionRecordNumber));
    }

    @Test
    public void shouldCreateNewPositionRecordSuccessfully(){
        long originalRecordSize = positionRepository.count();

        Position position = new Position();
        position.setName("Qwerty");

        positionRepository.save(position);

        assertNotNull(position.getId());
        assertThat(positionRepository.count(), is(++originalRecordSize));
    }
}
