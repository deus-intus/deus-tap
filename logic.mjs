const t=(v,max=500)=>String(v??'').trim().slice(0,max);
export const owner={name:'Anthon Linton',title:'Founder',company:'Deus Intus',website:'https://www.deusintus.com',email:'deus.intus.111@gmail.com',phone:''};
export const publicUrl='https://deus-intus.github.io/deus-tap/';
const esc=(v)=>t(v).replace(/\\/g,'\\\\').replace(/\r\n|\r|\n/g,'\\n').replace(/;/g,'\\;').replace(/,/g,'\\,');
export function contactVCard(person=owner){
 const parts=['BEGIN:VCARD','VERSION:3.0','N:'+esc(person.name||'')+';;;;','FN:'+esc(person.name||''),'ORG:'+esc(person.company||''),'TITLE:'+esc(person.title||'')];
 if(person.phone)parts.push('TEL;TYPE=CELL:'+esc(person.phone));
 if(person.email)parts.push('EMAIL;TYPE=INTERNET:'+esc(person.email));
 if(person.website)parts.push('URL:'+esc(person.website));
 return [...parts,'END:VCARD',''].join('\r\n');
}
export function validateContact(input){
 const name=t(input.name,100),email=t(input.email,200).toLowerCase(),phone=t(input.phone,40),company=t(input.company,120),notes=t(input.notes,500);
 const share_contact=t(input.share_contact)==='yes';
 const ok=share_contact&&name.length>=2&&(!!email||!!phone)&&(!email||/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email))&&(!phone||/^[+\d() .\-]{7,40}$/.test(phone));
 return {ok,data:{name,email,phone,company,notes,share_contact:share_contact?'yes':'no',fax:t(input.fax)}};
}
