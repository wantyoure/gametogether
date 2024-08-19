package company.solo.gametogether.controller;

import company.solo.gametogether.dto.boarddto.CreateBoardDto;
import company.solo.gametogether.entity.Board;
import company.solo.gametogether.service.boradsevice.BoardServiceImpl;
import company.solo.gametogether.service.boradsevice.BoradService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boradService;

    //게시판 생성
    @PostMapping()
    public CreateBoardDto createBoard(@RequestBody Board board) {
       return boradService.createBoard(board);
    }

    @PutMapping("/{boardId}")
    public void updateBoard(@PathVariable("boardId") Long boardId, Board board) {
        boradService.updateBoard(boardId, board);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable("boardId") Long boardId) {
        boradService.deleteBoard(boardId);
    }

    @GetMapping("/list")
    public List<CreateBoardDto> boardList(CreateBoardDto createBoardDto) {
        return boradService.boardList(createBoardDto);
    }
}
