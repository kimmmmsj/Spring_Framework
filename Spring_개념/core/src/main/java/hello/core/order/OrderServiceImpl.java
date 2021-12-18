package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{



    //이렇게 바꿔끼면 인터페이스뿐 아니라 구현클래스에도 의존하기 때문에 OCP/DIP를 위반!!
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //그래서 인터페이스에만 의존할 수 있도록 아래와 같이 바꿔준다.(+AppConfig의 등장!)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    //인터페이스에만 의존하므로 철저하기 DIP를 지킨다!

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public Order createOrder(Long memeberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memeberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memeberId, itemName, itemPrice, discountPrice);
    }
}
