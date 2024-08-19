package company.solo.gametogether.service.boradsevice;

import company.solo.gametogether.dto.boarddto.CreateBoardDto;
import company.solo.gametogether.entity.Board;
import company.solo.gametogether.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoradService {

    private final BoardRepository boardRepository;

    //게시판 생성
    @Override
    @Transactional(readOnly = false)
    public CreateBoardDto createBoard(Board board) {
        Board createBoard = boardRepository.save(board);
        return CreateBoardDto.builder().id(createBoard.getId())
                .content(createBoard.getContent())
                .title(createBoard.getTitle())
                .image(createBoard.getImage())
                .build();
    }

    //게시판 수정
    @Override
    @Transactional(readOnly = false)
    public void updateBoard(Long boardId, Board board) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isPresent()) {
            Board findBoards = findBoard.get();
            findBoards.setContent(board.getContent());
            findBoards.setTitle(board.getTitle());
            findBoards.setImage(board.getImage());
        }
    }

    //게시판 삭제
    @Override
    @Transactional(readOnly = false)
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    //게시판 리스트
    @Override
    public List<CreateBoardDto> boardList(CreateBoardDto createBoardDto) {
        return boardRepository.findAll().stream()
                .map(board -> new CreateBoardDto(
                        board.getId(),
                        board.getTitle(),
                        board.getContent(),
                        board.getImage()))
                .collect(Collectors.toList());
      }
}


