package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.TermUtil;
import ir.segroup.unipoll.ws.model.entity.TermEntity;
import ir.segroup.unipoll.ws.model.response.TermResponse;
import ir.segroup.unipoll.ws.repository.TermRepository;
import ir.segroup.unipoll.ws.service.TermService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;

    private final TermUtil termUtil;

    public TermServiceImpl(TermRepository termRepository, TermUtil termUtil) {
        this.termRepository = termRepository;
        this.termUtil = termUtil;
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAllTerms() {
        List<TermResponse> termEntityList = termRepository.findAll().stream()
                .map(termUtil::convert)
                .toList();
        return termUtil.createResponse(termEntityList, HttpStatus.OK);
    }
}
