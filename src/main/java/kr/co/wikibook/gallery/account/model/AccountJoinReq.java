package kr.co.wikibook.gallery.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
//@NoArgsConstructor //기본생성자 만드는
@Getter
public class AccountJoinReq {
    private String name;
    private String loginId;
    private String loginPw;
}
