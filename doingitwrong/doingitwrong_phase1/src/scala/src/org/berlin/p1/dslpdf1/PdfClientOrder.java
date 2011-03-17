/**
 * Copyright (c) 2006-2010 Berlin Brown and botnode.com  All Rights Reserved
 *
 * http://www.opensource.org/licenses/bsd-license.php

 * All rights reserved.

 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:

 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * * Neither the name of the Botnode.com (Berlin Brown) nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Date: 1/5/2009, updated 5/5/2010, added Scala 2.8rc3
 * 
 * Example DSL in Scala
 * 
 * Original created by: Debasish Ghosh
 * 
 * http://debasishg.blogspot.com/2009/07/dsl-composition-techniques-in-scala.html
 */

package org.berlin.p1.dslpdf1;

import java.util.ArrayList;
import java.util.List;

public class PdfClientOrder {

    public enum BuySell {
        BUY, SELL
    }

    private String accountNo;

    private List<PdfLineItem> lineItems = new ArrayList<PdfLineItem>();

    public PdfClientOrder(final List<PdfLineItem> lineItems, final String acct) {
        this.lineItems = lineItems;
        this.accountNo = acct;
    }

    public String toString() {
        return this.toStringBasic();
    }

    public String toStringBasic() {
        return "@ClientOrder - " + this.accountNo + " line-items : { "
                + ((lineItems.size() > 0) ? lineItems.get(0) : "empty") + "}";
    }

    public List<PdfLineItem> getLineItems() {
        return lineItems;
    }
    
    public String getAccountNo() {
        return accountNo;
    }

} // End of the Class //