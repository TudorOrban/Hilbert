import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewChatHeaderComponent } from './new-chat-header.component';

describe('NewChatHeaderComponent', () => {
  let component: NewChatHeaderComponent;
  let fixture: ComponentFixture<NewChatHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewChatHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewChatHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
