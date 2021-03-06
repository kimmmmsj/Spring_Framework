package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    //이렇게 바꿔끼면 인터페이스뿐 아니라 구현클래스에도 의존하기 때문에 OCP/DIP를 위반!!
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //그래서 인터페이스에만 의존할 수 있도록 아래와 같이 바꿔준다.(+AppConfig의 등장!)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    //인터페이스에만 의존하므로 철저하기 DIP를 지킨다!
    //cf. final 키워드는 한번 바로 값이 지정되거나, 생성자로 인해서 값이 들어가면 그 이후에는 값 변경 불가

//lombok의 @RequiredArgsConstructor을 쓰면 생성자를 만들 필요 조차 없다!
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    //생성자가 딱 1개이면 @Autowired 생략가능하다

    @Override
    public Order createOrder(Long memeberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memeberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memeberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도!!
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
