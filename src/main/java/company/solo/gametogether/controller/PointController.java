package company.solo.gametogether.controller;

import company.solo.gametogether.service.pointservice.PointService;
import company.solo.gametogether.service.pointservice.PointServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointServiceImpl pointService;

    //이벤트 참여
    @GetMapping("/event/{memberId}")
    public void createPoint(@PathVariable("memberId") Long memberId) {

        pointService.pointSave(memberId);
    }

}
