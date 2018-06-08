package com.gadator.gadator.utils;

import com.gadator.gadator.DTO.TextMessageDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageInfo {

    private int maxPage;
    private int thisPage;

    public PageInfo(Page<TextMessageDTO> messagesPage)
    {
        this.setMaxPage(messagesPage.getTotalPages());
        this.setThisPage(messagesPage.getNumber());
    }

    public boolean hasNext()
    {
        if (thisPage + 1 == maxPage )
        {
            return false;
        }
        return true;
    }

    public int getNext()
    {
        if (hasNext())
        {
            return thisPage + 1;
        }
        else return thisPage;
    }

    public boolean hasPrevious()
    {
        if (thisPage == 0)
        {
            return false;
        }
        return true;
    }

    public int getPrevious()
    {
        if (hasPrevious())
        {

            return thisPage - 1;
        }
        else return thisPage;
    }

}
