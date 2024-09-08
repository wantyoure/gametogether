package company.solo.gametogether.controller;
import company.solo.gametogether.dto.boarddto.CreateBoardDto;
import company.solo.gametogether.entity.Board;
import company.solo.gametogether.service.boradsevice.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boradService;

    //게시판 생성
    @PostMapping
    public CreateBoardDto createBoard(@RequestBody Board board) {
       return boradService.createBoard(board);
    }

    //게시판 수정
    @PutMapping("/{boardId}")
    public void updateBoard(@PathVariable("boardId") Long boardId, Board board) {
        boradService.updateBoard(boardId, board);
    }

    //게시판 삭제
    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable("boardId") Long boardId) {
        boradService.deleteBoard(boardId);
    }

    //게시판 리스트
    @GetMapping("/list")
    public List<CreateBoardDto> boardList(CreateBoardDto createBoardDto) {
        return boradService.boardList(createBoardDto);
    }

}
