package company.solo.gametogether.service.boradsevice;

import company.solo.gametogether.dto.boarddto.CreateBoardDto;
import company.solo.gametogether.entity.Board;

import java.util.List;

public interface BoradService {

    CreateBoardDto createBoard(Board board);

    void updateBoard(Long boardId, Board board);

    void deleteBoard(Long boardId);

    List<CreateBoardDto> boardList(CreateBoardDto createBoardDto);
}
